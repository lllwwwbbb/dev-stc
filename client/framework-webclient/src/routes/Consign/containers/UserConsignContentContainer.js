import React, {Component} from 'react';
import {connect} from "react-redux";
import ConsignContentComponent from "../components/ConsignContentComponent";
import {} from 'UTILS/FetchUtil';
import {httpPut} from "UTILS/FetchUtil";
import {setConsignContent, setConsignStatus} from "../../../modules/ducks/Consign";

const mapStateToProps = (state) => {
    const {list, index} = state.Consign;
    return {
        values: list[index].consignation!==undefined?JSON.parse(list[index].consignation):{},
        consignData: list[index],
        disable: false,
        // buttons: buttons,
    }
};

const buttons = (dispatch) => [{
    content: "保存",
    onClick: (consignData,values) => {
        let url = "http://127.0.0.1:8000/services/consign";
        let data = {
            id: consignData.id,
            consignation: values,
        };
        httpPut(url, data, (result) => {
            if (result.status == 'SUCCESS') {
                // consignData.consignation = values;
                dispatch(setConsignContent(-1, values));
            }
            else {
                console.log("点击“保存”错误");
            }
        });
        //
    },
},{
    content: "提交",
    onClick: (consignData,values) => {
        //
        let url1 = "http://127.0.0.1:8000/services/consign";
        let data1 = {
            id: consignData.id,
            consignation: values,
        };
        httpPut(url1, data1, (result) => {
            if (result.status == 'SUCCESS') {
                // consignData.consignation = values;
                dispatch(setConsignContent(-1, values));
            }
            else {
                console.log("点击“提交”错误");
            }
        });

        let url2 = "http://127.0.0.1:8000/services/consignActiviti/" + consignData.processInstanceID;
        let data2 = {
            operation: "submit"
        };
        httpPut(url2, data2, (result) => {
            if (result.status == 'SUCCESS') {
                // consignData.status = "TobeCheck";
                dispatch(setConsignStatus(-1, "TobeCheck"));
            }
            else {
                console.log("点击“提交”错误");
            }
        });
        //
    },
},
];

const mapDispatchToProps = (dispatch) => {
    return {
        buttons: buttons(dispatch),
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ConsignContentComponent);
