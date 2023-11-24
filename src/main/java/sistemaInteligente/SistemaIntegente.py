import sys
import pandas as pd
import numpy as np

from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

def recomendar(base, id_prof):

    base = base.split("\n")

    for i in range(len(base)):
        base[i] = base[i].split(",")

    base = pd.DataFrame(base, columns=["id_aula", "id_prof", "perfil", "media"])
    base["id_aula"] = base["id_aula"].astype(int)
    base["id_prof"] = base["id_aula"].astype(int)
    base["perfil"] = base["id_aula"].astype(str)
    base["media"] = base["id_aula"].astype(float)

    base.set_index("id_prof", inplace=True)
    base.index.name = None

    id_prof = int(id_prof)

    if id_prof < 0:
        model = SentenceTransformer('all-MiniLM-L6-v2')

        embeddings = model.encode(np.array(base['perfil']))
        cos_sim_data = pd.DataFrame(cosine_similarity(embeddings), index=base.index)
    else:
        tamanho = len(base.index)   
        cos_sim_data = pd.DataFrame(np.ones(tamanho, tamanho), index=base.index)
    
    recomm = cos_sim_data.T.iloc[id_prof]
    recomm = pd.DataFrame(np.multiply(recomm, base['media'].values[:, np.newaxis]), index=base.index)
    recomm = recomm.sort_values(ascending=False)

    index_recom = recomm.index.tolist()

    profs_recom = base['id_aula'].iloc[index_recom].values

    return profs_recom

recomendar(sys.argv[0], sys.argv[1])