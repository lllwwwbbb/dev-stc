import React, {Component,PropTypes} from 'react';
import ConsignListComponent from "../components/ConsignListComponent";
import {connect} from "react-redux";
import {addTabAction} from "../../../modules/ducks/Layout";
import {setFilter} from "../../../modules/ducks/Consign"
import {UserConsignContentView} from "ROUTES/Consign";

// todo: 利用第二个参数ownProps来过滤，实现搜索，ownProps是被显示传入的属性值，不包括map进去的
const mapStateToProps = (state, ownProps) => {
    return {
        dataSource: state.Consign.list.filter(state.Consign.listFilter),
        enableNew: true,
    }
};

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        showContent: () => dispatch(addTabAction('details', '委托详情', UserConsignContentView)),
        newConsign: () => {
            //message.success('This is a prompt message for success, and it will disappear in 10 seconds', 10);
        },
        setListFilter: (newlistFilter) => dispatch(setFilter(newlistFilter)),
    }
};
export default connect(mapStateToProps, mapDispatchToProps)(ConsignListComponent);
