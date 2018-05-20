import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Row, Col, Card, Tabs, Select, Button, Icon, Table, Form, Input, Divider, Modal, message} from 'antd';

const { Column } = Table;
const Search = Input.Search;
const confirm = Modal.confirm;

export default class ContrastListComponent extends Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        setListFilter: PropTypes.func.isRequired,
        dataSource: PropTypes.array.isRequired,
        showContent: PropTypes.func.isRequired,
//        deleteContrast: PropTypes.func.isRequired,
//        getContrastList: PropTypes.func.isRequired,
        newContrast: PropTypes.func,
        enableNew: PropTypes.bool.isRequired,
    };

    componentDidMount() {
//        this.props.getContrastList();
    }

    columns = [{
        title:"合同ID",
        dataIndex:"id",
        sorter:(a, b) => a.id - b.id,
    }, {
        title:"状态",
        dataIndex:"state",
        render: (stateCode) => {
            switch(stateCode) {
                case 'TobeSubmit':
                    return '待提交';
                case 'TobeCheck':
                    return '待审核';
                case 'Finished':
                    return '已通过';
                default:
                    return '未定义状态';
            }
        },
        filters: [{
            text: '待提交',
            value: 'TobeSubmit',
        }, {
            text: '待审核',
            value: 'TobeCheck',
        }, {
            text: '已通过',
            value: 'Finished',
        }],
        filterMultiple: false,/*单选filter*/
        // specify the condition of filtering result
        // here is that finding the name started with `value`
        onFilter: (value, record) => record.state.indexOf(value) === 0,
    }, {
        title:"操作",
        dataIndex:"id",
        key:"operation",
        render: (id, record, index) => {
            return (
                <span>
                <Button type="default" onClick={this.viewContent(index)}><Icon type="eye-o" />查看详情</Button>
                <Divider type="vertical" />
                <Button type="danger" onClick={this.showDeleteConfirm(id)} ghost><Icon type="close-circle-o" />取消委托</Button>
                </span>
            )
        }
    }
    ];

    onSearch = (value) => {
        const reg = new RegExp(value, 'gi');
        this.props.setListFilter((record) => record.id.match(reg));
    };
    viewContent = (index) => () => {
        this.props.showContent(index);
    };
    showDeleteConfirm = (id) => () => {
        const ID=id;
        confirm({
            title: 'Are you sure delete this Contrast?',
            //content: 'Some descriptions',
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk: () => {
                this.props.deleteContrast(id);
            },
            onCancel() {},
        });
    };

    render() {
        return (
            <div>
                <h3 style={{ marginBottom: 16 }}>客户合同列表</h3>
                <Card>
                    <Search
                        placeholder="搜索合同ID"
                        onSearch={this.onSearch}
                        style={{ width: 200 }}
                    />
                </Card>
                <br />
                {this.props.enableNew ?
                    <Button type="primary" onClick={this.props.newContrast}><Icon type="plus-circle-o" />新建合同</Button>
                    : <br/>}
                <br /><br />
                <Table dataSource={this.props.dataSource} columns={this.columns} rowKey={'id'} />

            </div>
        );
    }
}