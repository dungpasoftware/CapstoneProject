import { LOAD_TABLE, LOAD_TABLE_SUCCESS, LOAD_TABLE_FAILURE } from "../common/actionType";

export const loadTable = payload => ({
    type: LOAD_TABLE,
    payload,
});

export const loadTableSuccess = payload => ({
    type: LOAD_TABLE_SUCCESS,
    payload,
});

export const loadTableFailure = payload => ({
    type: LOAD_TABLE_FAILURE,
    payload,
});

export default {
    loadTable,
    loadTableSuccess,
    loadTableFailure,
};