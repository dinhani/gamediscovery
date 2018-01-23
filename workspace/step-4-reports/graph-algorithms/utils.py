import os
import csv
import pandas as pd
import numpy as np
from neo4j.v1 import GraphDatabase, basic_auth

def neo4j_query(cypher):
    driver = GraphDatabase.driver(os.environ["GD_NEO4J_CONN_BOLT"], auth=basic_auth(os.environ["GD_NEO4J_CONN_USER"], os.environ["GD_NEO4J_CONN_PASSWORD"]))
    session = driver.session()
    result = session.run(cypher)
    session.close()

    return result

def neo4j_to_dataframe(records):
    # base dataframe
    rows = []

    for record in records:
        row = {}
        for key in record.keys():
            row[key] = record[key]
        rows.append(row)

    df = pd.DataFrame.from_records(rows, columns=records.keys())
    return df

def dataframe_to_wide(df):
    def isOne(row):
        s = np.sum(row)
        return 1 if s > 0 else 0
    df = df.pivot_table(index='id', columns='c2', aggfunc=isOne, fill_value=0).reset_index()

    return df
