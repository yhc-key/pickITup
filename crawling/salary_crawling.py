from elasticsearch import Elasticsearch
es = Elasticsearch([{'host': 'elasticsearch.pickitup.online', 'port':443, 'scheme': 'https'}])
# es = Elasticsearch([{'host': 'localhost', 'port': 9200, 'scheme': 'http'}])
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup

import time

base_url = "https://www.jobplanet.co.kr/job/#"

def get_all_company_names():    
    result = es.search(index="searchcompany", body={"query": {"match_all": {}}, "size": 10000})
    company_names = [hit["_source"]["name"] for hit in result["hits"]["hits"]]
    return company_names

def initialize_driver():
    # 웹 드라이버 설정
    chrome_options = Options()
    chrome_options.add_experimental_option("detach", True)

    # 다음 줄 주석 풀면 실제 크롤링하는 웹 페이지 안 나오게 할 수 있음
    # chrome_options.add_argument("headless")
    
    # 크롬 드라이버 설정
    driver = webdriver.Chrome(options=chrome_options)
    return driver

def switch_to_new_tab(driver, url):
    driver.execute_script("window.open('{}');".format(url))
    driver.switch_to.window(driver.window_handles[1])  # 새 탭으로 전환

def close_and_switch_to_original_tab(driver):    
    # 현재 탭을 닫고 원래 탭으로 돌아감
    driver.close()
    driver.switch_to.window(driver.window_handles[0])

def close_advertisement(driver):
    try:
        driver.get(base_url)
        wait = WebDriverWait(driver, 3)

        # iframe으로 전환
        iframe = wait.until(EC.presence_of_element_located((By.XPATH, "//iframe[@title='Modal Message']")))
        driver.switch_to.frame(iframe)
        print("iframe으로 전환...")

        # 닫기 버튼 클릭
        close_button = driver.find_element(By.CLASS_NAME, "bz-close-btn")
        close_button.click()
        print("광고 닫기 성공")
    except Exception as e:
        # 광고 모달창이 나타나지 않은 경우 예외 처리
        print(f"error modal not exist : {e}")
        pass
    
    finally:
        # 원래의 상위 레벨로 전환
        driver.switch_to.default_content()
        print("iframe에서 나가기...")

def login(driver, usr, pwd):
    driver.get("https://www.jobplanet.co.kr/users/sign_in?_nav=gb")
    time.sleep(2)

    # 아이디 입력
    login_id = driver.find_element(By.ID, "user_email")
    login_id.send_keys(usr)

    # 비밀번호 입력
    login_pwd = driver.find_element(By.ID, "user_password")
    login_pwd.send_keys(pwd)

    # 로그인 버튼 클릭
    login_id.send_keys(Keys.RETURN)
    time.sleep(3)

def search_data_id(driver):    
    search_box = driver.find_element(By.CLASS_NAME, "input_search")
    search_box.click()  # 검색창 클릭하여 포커스 활성화

    company_names = get_all_company_names()

    for company_name in company_names:        
        search_box.clear()  # 검색창 내용 지우기
        search_box.send_keys(company_name)  # 기업명 입력
        time.sleep(1)  # 검색어 자동 완성을 위해 잠시 대기

        # 자동완성된 기업명 목록 가져오기        
        auto_complete_list = driver.find_elements(By.CSS_SELECTOR, ".auto_key .company")
    
        # 가져온 목록에서 data-id 값 출력
        
        if auto_complete_list:
            data_id = auto_complete_list[0].get_attribute("data-id")
            print(f"기업명 '{company_name}'에 대한 자동완성: data-id = {data_id}")
            search_salary(company_name, data_id, driver)
        else:
            print(f"자동완성된 기업이 없습니다.")

def search_salary(company_name, data_id, driver):    
    pre_url = "https://www.jobplanet.co.kr/companies/"    
    url = pre_url + data_id + "/salaries/" + company_name
    try:
        switch_to_new_tab(driver, url)

        # find_click_close_button(driver)

        page_source = driver.page_source
        soup = BeautifulSoup(page_source, "html.parser")       
        # 평균 연봉 추출
        average_salary = soup.find("div", class_="num").text.strip().replace("만원", "").replace(",", "").replace(" ", "")
        print("웹개발 평균 연봉:", average_salary)

        # Elasticsearch에서 회사 정보 가져오기
        company_info = es.search(index='searchcompany', body={"query": {"match": {"name": company_name}}})['hits']['hits'][0]['_source']
        
        # 문서의 아이디 가져오기
        doc_id = es.search(index='searchcompany', body={"query": {"match": {"name": company_name}}})['hits']['hits'][0]['_id']
        
        # 연봉 정보 추가
        company_info['salary'] = average_salary
        
        # Elasticsearch에 업데이트
        es.index(index='searchcompany', id=doc_id, body=company_info)
    except Exception as e:        
        print(f"Error fetching post body: {e}")

    finally:
        close_and_switch_to_original_tab(driver)

def find_click_close_button(driver):
    time.sleep(3)
    # 닫기 버튼을 찾습니다.
    close_button = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.CLASS_NAME, "btn_delete_follow_banner"))
    )

    # 닫기 버튼을 클릭합니다.
    driver.execute_script("arguments[0].click();", close_button)
    time.sleep(3)

def run():    
    USR = "thd4525@koreatech.ac.kr"
    PWD = "thddbswo1!"
    driver = initialize_driver()
    close_advertisement(driver)
    login(driver, USR, PWD)
    search_data_id(driver)

run()