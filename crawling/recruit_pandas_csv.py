import os
import pandas as pd
from datetime import datetime
# import configparser

# config = configparser.ConfigParser()
# config.read('C:\\SSAFY\\yutw\\project\\searchrecruit\\crawler\\crawler.conf')

def to_csv(data):

    pathlink ="C:\\SSAFY\\yutw\\data\\searchrecruit"

    # db create
    if not os.path.isdir(pathlink):
        os.mkdir(pathlink)

    # present_date = str(datetime.now())[:10]

    data_df = pd.DataFrame([data])  # 데이터프레임으로 변환
    
    file_path = os.path.join(pathlink, "recruitdata.csv")
    # file_path = os.path.join(pathlink, "recruitdata_local.csv")

    if os.path.exists(file_path):
        # 파일이 이미 존재하면 append 모드로 추가
        data_df.to_csv(file_path, mode='a', header=False, index=False, encoding='utf-8-sig')
    else:
        # 파일이 없으면 빈 데이터프레임을 생성하여 저장
        empty_df = pd.DataFrame(columns=data_df.columns)  # 데이터프레임 컬럼 구조를 유지하기 위해
        empty_df.to_csv(file_path, index=False, encoding='utf-8-sig')

        # 생성한 빈 데이터프레임에 데이터 추가
        data_df.to_csv(file_path, mode='a', header=False, index=False, encoding='utf-8-sig')