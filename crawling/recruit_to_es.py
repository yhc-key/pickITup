

from elasticsearch import Elasticsearch
es = Elasticsearch([{'host': 'elasticsearch.pickitup.online', 'port':443, 'scheme': 'https'}])
# es = Elasticsearch([{'host': 'localhost', 'port': 9200, 'scheme': 'http'}])
import json
# import configparser
from datetime import datetime, timedelta
import pandas as pd
import recruit_pandas_csv

# config = configparser.ConfigParser()
# config.read('C:\\SSAFY\\yutw\\project\\searchrecruit\\crawler\\crawler.conf')

def to_elastic(data):
    existing_url = es.search(index="searchrecruit", body={"query": {"match_phrase": {"url": data['url']}}})
    if existing_url['hits']['total']['value'] > 0:
        print("이미 존재하는 공고입니다...")
        return    
    # print("안넘어왔지? : ", existing_url)
    recruit_pandas_csv.to_csv(data)

    pathlink ="C:\\SSAFY\\yutw\\data\\searchrecruit"

    # present_date = str(datetime.utcnow() + timedelta(hours=9))[:10]
    del_date = str(datetime.utcnow() - timedelta(hours=39))[:10]
    
    cnt = len(pd.read_csv(pathlink + "/" + "recruitdata.csv", index_col=0).index)
    # cnt = len(pd.read_csv(pathlink + "/" + "recruitdata_local.csv", index_col=0).index)
    es.index(index="searchrecruit", id=str(cnt), body=json.dumps(data))

    if cnt == 1:
        days = [x for x in range(1, int(del_date[-2:]))]
        for day in days:
            if len(str(day)) == 1:
                for each in range(1, 1500):
                    try:
                        es.delete(index="searchrecruit", doc_type="recruit", id=del_date[:8] + '0' + str(day) + "-" + str(each))
                    except:
                        pass
            else:
                for each in range(1, 1500):
                    try:
                        es.delete(index="searchrecruit", doc_type="recruit", id=del_date[:8] + str(day) + "-" + str(each))
                    except:
                        pass