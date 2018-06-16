import {baseServiceAddress, STATUS} from "SERVICES/common";
import {httpDelete, httpGet, httpPost, httpPut} from "UTILS/FetchUtil";
import {removeConsign, setConsignContent, setConsignList/*, setConsignState*/} from "../modules/ducks/Consign";
import {mockProjectData, valueData} from "./mockData";
import {STATE} from "./common";

const consignBase = baseServiceAddress + '/consign';
const consignActivitiBase = baseServiceAddress + '/processInstance';

export const getConsignList = (dispatch, callback) => {
    httpGet(consignBase,(result) => {
        const {status, data} = result;
        if (status === STATUS.SUCCESS) {
            dispatch(setConsignList(data));
        }
        callback && callback(status);
    });
};

export const getConsign = (dispatch, id, callback) => {
    httpGet(consignBase + '/' + id, (result) => {
        const {status, data} = result;
        if (status === STATUS.SUCCESS) {
            dispatch(setConsignContent(data));
        }
        callback && callback(status);
    });
};

export const deleteConsign = (dispatch, id, callback) => {
    httpDelete(consignBase, {id:id}, (result) => {
        // console.log("before remove");
        // dispatch(removeConsign(id));
        const {status} = result;
        if(status === STATUS.SUCCESS)
            dispatch(removeConsign(id));
        callback && callback(status);
    });
};

export const newConsign = (dispatch, callback) => {
    httpPost(consignBase, {consignation:null,}, (result) => {
        const {data, status} = result;
        if (status === STATUS.SUCCESS) {
            dispatch(setConsignContent(data));
        }
        callback && callback(status);
    });
};

export const updateConsign = (dispatch, data, callback) => {
    //console.log(data);
    httpPut(consignBase, data, (result) => {
        const {status, data} = result;
        if (status === STATUS.SUCCESS) {
            dispatch(setConsignContent(data));
        }
        callback && callback(status);
    });
};

export const getConsignState = (dispatch, processInstanceID, id, callback) => {
    httpGet(consignActivitiBase + '/' + processInstanceID, (result) => {
        const {status, data} = result;
        const {operation} = data;
        sessionStorage.setItem('operation',JSON.stringify(operation));
        if (status === STATUS.SUCCESS) {
            const newData = {
                ...data,
                id: id,
            };
            dispatch(setConsignContent(newData));
        }

        callback && callback(status);
    })
};

export const putConsignState = (dispatch, processInstanceID, data, id, callback) => {
    // console.log("ID = " + processInstanceID);
    console.log(consignActivitiBase + '/' + processInstanceID);
    httpPut(consignActivitiBase + '/' + processInstanceID, data, (result) => {
        const {status,data} = result;
        if (status === STATUS.SUCCESS) {
             const newData = {
                ...data,
                id: id,
            };
            dispatch(setConsignContent(newData));
        }
        callback && callback(status);
    });
};