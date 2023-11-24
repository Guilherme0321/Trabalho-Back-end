import sys
import pandas as pd
import numpy as np

from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity


def recomendar(base, id_prof):

    base = base.split("\n")
    id_prof = int(id_prof)
    rows = []
    for i in range(len(base)):
        if base[i] != "":
            rows.append(base[i].split(","))

    dataframe = pd.DataFrame(rows, columns=["id_prof", "id_aula", "perfil", "media"])
    dataframe["id_aula"] = dataframe["id_aula"].astype(int)
    dataframe["id_prof"] = dataframe["id_prof"].astype(int)
    dataframe["perfil"] = dataframe["perfil"].astype(str)
    dataframe["media"] = dataframe["media"].astype(float)

    dataframe.set_index("id_prof", inplace=True)
    dataframe.index.name = None

    if id_prof < 0:
        model = SentenceTransformer('all-MiniLM-L6-v2')

        embeddings = model.encode(np.array(dataframe['perfil']))
        cos_sim_data = pd.DataFrame(cosine_similarity(embeddings), index=dataframe.index)
    else:
        tamanho = len(dataframe.index)
        cos_sim_data = pd.DataFrame(np.ones(tamanho, tamanho), index=dataframe.index)

    recomm = cos_sim_data.T.iloc[id_prof]
    recomm = np.multiply(recomm, dataframe['media'].values)
    recomm = recomm.sort_values(ascending=False)
    
    index_recom = recomm.index.tolist()
    
    profs_recom = dataframe.loc[index_recom, :]

    profs_recom =  dataframe['id_aula'].values 

    return profs_recom

print(recomendar(sys.argv[1], sys.argv[2]))
