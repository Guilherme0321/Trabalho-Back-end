import sys
import pandas as pd
import numpy as np

from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

def recomendar(base, id_prof):

    base = pd.read_csv(base, columns=["id_aula", "id_prof", "perfil", "media"])
    id_prof = int(id_prof)

    if id_prof < 0:
        model = SentenceTransformer('all-MiniLM-L6-v2')

        embeddings = model.encode(np.array(base['perfil']))
        cos_sim_data = pd.DataFrame(cosine_similarity(embeddings))
    else:
        tamanho = len(base.index)   
        cos_sim_data = pd.DataFrame(np.ones(tamanho, tamanho))
    
    recomm = cos_sim_data.T.iloc[id_prof]
    recomm = pd.DataFrame(np.multiply(recomm, base['media'].values[:, np.newaxis]))
    recomm = recomm.sort_values(ascending=False)

    index_recom = recomm.index.tolist()
    profs_recom = base['id_aula'].iloc[index_recom].values

    return profs_recom

recomendar(sys.argv[0], sys.argv[1])