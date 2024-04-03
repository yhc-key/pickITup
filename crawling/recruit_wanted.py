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

base_url = "https://www.wanted.co.kr/wdlist/518?country=kr&job_sort=job.recommend_order&years=-1&locations=all/#"
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

        # 새 탭에서 요소들이 로드될 때까지 기다림        
        while driver.execute_script("return document.readyState;") != "complete":
            pass

        # 상세 정보 더 보기 텍스트를 가진 버튼을 찾아 클릭
        detail_button = wait.until(EC.presence_of_element_located((By.XPATH, "//button[contains(., '상세 정보 더 보기')]")))
        detail_button.click()

        while driver.execute_script("return document.readyState;") != "complete":
            pass
        
        page_source = driver.page_source
        soup = BeautifulSoup(page_source, "html.parser")

        # 경력 추출
        try :
            career_span = soup.find('span', string='∙').find_next('span').find_next('span')
            if career_span:
                next_span = career_span.find_next('span')
                career_text = next_span and next_span.get_text(strip=True)
                
                if '경력' in career_text or '신입' in career_text:
                    career = career_text
                else:
                    print("경력 또는 신입 정보를 찾을 수 없습니다.")
            else:
                print("∙ 문자가 포함된 span 태그를 찾을 수 없습니다.")
        except :
            career = ""

        # 자격요건 추출
        try :
            qualification_requirements_element = soup.find('h3', string='자격요건')
            qualification_requirements = qualification_requirements_element.find_next('p').get_text(strip=True) if qualification_requirements_element else ''
        except :
            qualification_requirements = ""

        # 우대사항 추출
        try :
            preferred_requirements_element = soup.find('h3', string='우대사항')
            preferred_requirements = preferred_requirements_element.find_next('p').get_text(strip=True) if preferred_requirements_element else ''
        except :
            preferred_requirements = ""

        # 마감일 추출
        try :
            due_date_element = soup.find('h2', string='마감일')
            due_date_str = due_date_element.find_next('span').get_text(strip=True) if due_date_element else ''
            due_date = parse_date(due_date_str)
        except :
            due_date = ""

        # 주소 추출
        try :
            address = (soup
                    .find('div', class_='JobWorkPlace_JobWorkPlace__map__location__Jksjp')
                    .find('span', class_='Typography_Typography__root__xYuMs')
                    .get_text(strip=True))
        except :
            address = ""

        return {"qualification_requirements": qualification_requirements, 
                "preferred_requirements": preferred_requirements, 
                "due_date": due_date,
                "career": career,
                "address": address}

    except Exception as e:        
        print(f"Error fetching post body: {e}")
        return {"qualification_requirements": "", "preferred_requirements": "", "due_date": "2100-01-01", "career": "", "address": ""}
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
    return soup.select('.Card_Card__lU7z_')

def extract_post_data(driver, post):    
    # 제목 추출
    title_class_contains = 'JobCard_JobCard__body__position__'
    title = post.select_one(f'p[class*="{title_class_contains}"]').get_text(strip=True)

    # 회사명 추출    
    pattern = re.compile(r'\([^)]*\)')
    company_class_contains = 'JobCard_JobCard__body__company__'
    company_text = post.select_one(f'span[class*="{company_class_contains}"]').get_text(strip=True)
    company = re.sub(pattern, '', company_text).strip()

    # URL 추출
    pre_url = "https://www.wanted.co.kr"
    post_url_tag = post.select_one('a')
    post_url = pre_url + post_url_tag.get('href', '') if post_url_tag else ''

    # 이미지 태그 추출
    image_tag = post.select_one('.JobCard_JobCard__thumb__iNW6E img')
    # 이미지 URL 추출
    thumbnail_url = image_tag['src'] if image_tag else ''

    post_data = {"title": title, "company": company, "url": post_url, "thumbnail_url": thumbnail_url}
    post_data.update(get_post_body(driver, post_url))  # 추가 정보 추출
    return post_data

def collecting(base_url):
    driver = initialize_driver()        
    try:
        driver.get(base_url)

        post_list = get_post_list(driver)        
        print("post_list의 길이:", len(post_list))

        # 시간 설정
        collect_time = str(datetime.now())[:10]

        for post in post_list:
            post_data = extract_post_data(driver, post)
            
            # 데이터 저장 형식 설정
            insert_data_recruit = {
                "source": "recruit_wanted",
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