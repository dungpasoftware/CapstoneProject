import Axios  from "./index";

export const getAll = (token) => {
  return Axios.get(`/materials`,{
    headers: {
      token
    }
  });
};

export const getById = (token, materialId) => {
  return Axios.get(`/materials/${materialId}`, {
    headers: {
      token
    }
  })
};

export const searchAll = (token, {id, name, page}) => {
  return Axios.get(`materials/search?id=${id}&name=${name}&page=${page}`, {
    headers: {
      token
    }
  })
}

export const editById = (token, materialData) => {
  return Axios.put(`/materials/${materialData.materialId}`, materialData, {
    headers: {
      token
    }
  })
};

export const getImportMaterialDetail = (token, id) => {
  return Axios.get(`/materials/import-material-detail/${id}`,{
    headers: {
      token
    }
  })
}

export const getReportDetail = (token, id) => {
  return Axios.get(`/materials/import-export/${id}`, {
    headers: {
      token
    }
  })
}
