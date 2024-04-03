#-*- coding: utf-8 -*-
import os
import sys
from bs4 import BeautifulSoup

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException

from urllib.request import urlopen, Request
from datetime import datetime
import time
import re

dir = os.path.dirname(__file__)
sys.path.insert(0, os.path.join(dir, '../save'))

import recruit_to_es
import company_to_es

base_url = "https://www.jobplanet.co.kr/job/#"
SCROLL_PAUSE_TIME = 1

def initialize_driver():
    # 웹 드라이버 경로 지정
    driver_path = '/path/to/chromedriver'

    # 웹 드라이버 설정
    chrome_options = Options()
    chrome_options.add_experimental_option("detach", True)
    # 다음 줄 주석 풀면 실제 크롤링하는 웹 페이지 안 나오게 할 수 있음
    # chrome_options.add_argument("headless")
    
    # 크롬 드라이버 설정
    driver = webdriver.Chrome(options=chrome_options)
    return driver

def switch_to_new_tab(driver, post_url):
    driver.execute_script("window.open('{}');".format(post_url))
    driver.switch_to.window(driver.window_handles[1])  # 새 탭으로 전환

def close_and_switch_to_original_tab(driver):    
    # 현재 탭을 닫고 원래 탭으로 돌아감
    driver.close()
    driver.switch_to.window(driver.window_handles[0])

def scroll_to_bottom(driver):
    last_height = driver.execute_script("return document.body.scrollHeight")
    # for _ in range(2):
    while(True):
        # 스크롤 끝까지 내리기
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")

        # 페이지 로딩 기다리기    
        time.sleep(SCROLL_PAUSE_TIME)            
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight-50);")            
        time.sleep(SCROLL_PAUSE_TIME)
        
        new_height = driver.execute_script("return document.body.scrollHeight")

        if new_height == last_height:
            print("2관문")
            time.sleep(SCROLL_PAUSE_TIME)            
            driver.execute_script("window.scrollTo(0, document.body.scrollHeight-50);")            
            time.sleep(SCROLL_PAUSE_TIME)
            
            new_height = driver.execute_script("return document.body.scrollHeight")
            if new_height == last_height:
                print("3관문")
                time.sleep(SCROLL_PAUSE_TIME)            
                driver.execute_script("window.scrollTo(0, document.body.scrollHeight-50);")            
                time.sleep(SCROLL_PAUSE_TIME)
                
                new_height = driver.execute_script("return document.body.scrollHeight")
                if new_height == last_height:
                    print("4관문")
                    time.sleep(SCROLL_PAUSE_TIME)            
                    driver.execute_script("window.scrollTo(0, document.body.scrollHeight-50);")            
                    time.sleep(SCROLL_PAUSE_TIME)
                    
                    new_height = driver.execute_script("return document.body.scrollHeight")
                    if new_height == last_height:
                        print("5관문")
                        time.sleep(SCROLL_PAUSE_TIME)            
                        driver.execute_script("window.scrollTo(0, document.body.scrollHeight-50);")            
                        time.sleep(SCROLL_PAUSE_TIME)
                        
                        new_height = driver.execute_script("return document.body.scrollHeight")
                        if new_height == last_height:
                            print("6관문")
                            time.sleep(SCROLL_PAUSE_TIME)            
                            driver.execute_script("window.scrollTo(0, document.body.scrollHeight-50);")            
                            time.sleep(SCROLL_PAUSE_TIME)
                            
                            new_height = driver.execute_script("return document.body.scrollHeight")
                            if new_height == last_height:
                                break

        last_height = new_height

def get_post_body(driver, post_url):    
    wait = WebDriverWait(driver, 10)
    try:
        switch_to_new_tab(driver, post_url)                

        # WebDriverWait를 통한 마감일 태그 로딩 대기
        wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, 'dd.recruitment-summary__dd')))        

        page_source = driver.page_source
        soup = BeautifulSoup(page_source, "html.parser")        
        
        # 마감일 추출
        try :
            due_date_tag = soup.find('dd', class_ = "recruitment-summary__dd")
            due_date_str = due_date_tag.find_next('span').get_text(strip=True) if due_date_tag else ''
            due_date = parse_date(due_date_str)
        except :
            due_date = ""

        # 경력 추출
        try :
            career_tag = due_date_tag.find_next('dt').find_next('dt')
            career = career_tag.find_next('dd').get_text(strip=True) if career_tag else ''       
        except :
            career = ""

        # 스킬 추출하여 자격요건에 추가
        try :
            skill_tag = career_tag.find_next('dt').find_next('dt').find_next('dt')
            qualification_requirements = skill_tag.find_next('dd').get_text(strip=True) if skill_tag else ''     
        except :
            qualification_requirements = ""

        # 자격요건 추출
        try :
            qualification_requirements_element = soup.find('h3', string='자격 요건')
            qualification_requirements += " "
            qualification_requirements += qualification_requirements_element.find_next('p').get_text(strip=True) if qualification_requirements_element else ''
        except :
            qualification_requirements = ""

        # 우대사항 추출
        try :
            preferred_requirements_element = soup.find('h3', string='우대사항')
            preferred_requirements = preferred_requirements_element.find_next('p').get_text(strip=True) if preferred_requirements_element else ''
        except :
            preferred_requirements = ""
        # 주소 추출
        try :
            address_element = soup.find('h3', string='회사위치')
            address = address_element.find_next('p').get_text(strip=True) if address_element else ''
        except :
            address = ""

        return {
            "qualification_requirements": qualification_requirements, 
                "preferred_requirements": preferred_requirements, 
                "due_date": due_date,
                "career": career,
                "address": address
                }

    except Exception as e:        
        print(f"Error fetching post body: {e}")
        return {
            "qualification_requirements": "", "preferred_requirements": "", 
            "due_date": "2100-01-01", "career": "", "address": ""
            }
    finally:
        close_and_switch_to_original_tab(driver)

def parse_date(date_str):
    # if date_str == "상시채용":
    #     return "2100-01-01"

    formats = ["%Y-%m-%d", "%Y.%m.%d", "%y.%m.%d", "%d.%m.%Y", "%d.%m.%y", 
               "%m.%d.%Y", "%m.%d.%y", "%m.%d일", "%m.%d일", "%m.%d", "%m-%d", "%d.%m"]

    for fmt in formats:
        try:
            return datetime.strptime(date_str, fmt).strftime("%Y-%m-%d")
        except ValueError:
            pass

    return "2100-01-01"

def get_post_list(driver):
    scroll_to_bottom(driver)

    # 스크롤 이후의 페이지 소스 가져오기
    page_source = driver.page_source
    
    # BeautifulSoup을 사용하여 데이터 파싱
    soup = BeautifulSoup(page_source, "html.parser")

    # 리스트 추출
    return soup.select('.item-card')

def extract_post_data(driver, post):       
    # 제목 추출
    title = post.select_one('.item-card__title').text.replace('공고 명 :','').strip()

    # 회사명 추출
    pattern = re.compile(r'\([^)]*\)')
    company_text = post.select_one('.item-card__name').text.replace('회사 명 :','').strip()
    company = re.sub(pattern, '', company_text).strip()

    # URL 추출
    pre_url = "https://www.jobplanet.co.kr"
    post_url_tag = post.select_one('a')
    post_url = pre_url + post_url_tag.get('href', '') if post_url_tag is not None else ''
    # post_url = post_url_tag.get('href', '') if post_url_tag else ''

    # 이미지 태그 추출    
    image_span = post.select_one('.thumbnail_image')
    # 이미지 URL 추출
    if image_span:
        style_attribute = image_span.get('style')
        thumbnail_url = style_attribute.split('url("')[1].split('")')[0]
    else:                
        print("No image found.") # 여기에 기본 이미지 넣기

    post_data = {"title": title, "company": company, "url": post_url, "thumbnail_url": thumbnail_url}
    post_data.update(get_post_body(driver, post_url))  # 추가 정보 추출
    return post_data

def collecting(base_url):
    driver = initialize_driver()    
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

    # 직종 필터링 버튼 클릭
    job_button = driver.find_element(By.XPATH, "//button[@class='jply_btn_sm inner_text jf_b2' and contains(text(), '직종')]")
    job_button.click()
    print("직종 버튼 클릭 성공...")  

    # "개발" 버튼 클릭
    dev_button = driver.find_element(By.XPATH, "//button[@class='filter_depth1_btn jf_b1' and contains(text(), '개발')]")
    dev_button.click()
    print("개발 버튼 클릭 성공...")

    # 체크박스 선택
    checkbox_label = driver.find_element(By.XPATH, "//label[@class='jply_checkbox_box' and span[@class='jf_b1' and contains(text(), '개발 전체')]]")
    checkbox_label.click()
    print("체크박스 클릭 성공...")

    # 적용 버튼 클릭
    accept_button = driver.find_element(By.XPATH, "//button[@class='jply_btn_sm ty_solid' and contains(text(), '적용')]")
    accept_button.click()
    print("적용 버튼 클릭 성공...")  
    time.sleep(2)

    try:
        post_list = get_post_list(driver)
        print("post_list의 길이:", len(post_list))

        # 시간 설정
        collect_time = str(datetime.now())[:10]

        for post in post_list:
            post_data = extract_post_data(driver, post)      

            # 데이터 저장 형식 설정
            insert_data_recruit = {
                "source": "recruit_jobplanet",
                "collect_time": collect_time,
                **post_data
            }

            insert_data_company ={
                "name": post_data.get("company"),
                "address":post_data.get("address"),
                "salary":""
            }

            # 데이터 출력 또는 저장
            # print(insert_data)
            # recruit_pandas_csv.to_csv(insert_data)
            recruit_to_es.to_elastic(insert_data_recruit)
            company_to_es.to_elastic(insert_data_company)
    finally:
        time.sleep(3)
        # driver.quit()  # 브라우저 닫기

# 함수 호출
collecting(base_url)