import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Row, Col, Card, Tabs, Select, Button, Icon, Table, Form, Input, Divider, Modal, message, Badge, Steps,Layout,Menu,List} from 'antd';
import {STATE} from "../../../services/common"

const Search = Input.Search;
const confirm = Modal.confirm;
const InputGroup = Input.Group;
const Option = Select.Option;
const Step = Steps.Step;
const { SubMenu } = Menu;
const { TextArea } = Input;
const { Header, Content, Footer } = Layout;

export default class ProjectContentComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            current: 0,
        };
    }

    static propTypes = {
        setContentFilter: PropTypes.func,
        dataSource: PropTypes.array,
        showContent: PropTypes.func,
        //deleteProject: PropTypes.func,
        //getProjectContent: PropTypes.func,
    };

    componentDidMount() {
        //this.props.getProjectList();
    }


    steps = [{
        title: '委托',
        description: "委托已通过",
        //content: 'First-content',
    }, {
        title: '合同',
        description: "This is a description.",
        //content: 'Second-content',
    }, {
        title: '测试',
        description: "This is a description.",
        //content: 'Last-content',
    }, {
        title: '报告',
        description: "This is a description.",
        //content: 'Last-content',
    }, {
        title: '结项',
        description: "This is a description.",
        //content: 'Last-content',
    }];

    data = [
        '委托申请表',
        '测试合同书',
        '合同评审表',
        '测试方案书',
        '测试用例表',
        '测试记录表',
        '测试问题清单',
        '测试报告书',
        '测试报告检查表',
        '测试工作检查表',
        '满意度调查表'
    ]
    /*查看详情*/
    viewContent = (record) => () => {
        this.props.showContent(record);
    };

    render() {
        return (
            <div>
                {/*<Layout style={{ padding: '24px 0', background: '#fff' }}>
                    <Sider width={200} style={{ background: '#fff' }}>
                        <Menu
                            mode="inline"
                            defaultSelectedKeys={['1']}
                            defaultOpenKeys={['sub1']}
                            style={{ height: '100%' }}
                        >
                                <Menu.Item key="1">option1</Menu.Item>
                                <Menu.Item key="2">option2</Menu.Item>
                                <Menu.Item key="3">option3</Menu.Item>
                                <Menu.Item key="4">option4</Menu.Item>
                                <Menu.Item key="5">option5</Menu.Item>
                                <Menu.Item key="6">option6</Menu.Item>
                                <Menu.Item key="7">option7</Menu.Item>
                                <Menu.Item key="8">option8</Menu.Item>
                                <Menu.Item key="9">option9</Menu.Item>
                                <Menu.Item key="10">option10</Menu.Item>
                                <Menu.Item key="11">option11</Menu.Item>
                                <Menu.Item key="12">option12</Menu.Item>
                        </Menu>
                    </Sider>
                    <Content style={{ padding: '0 24px', minHeight: 280 }}>*/}
                    <Layout style={{ background: '#fff' }}>
                <h3>流程详情</h3>
                        <Card title="流程进度"
                              //bordered={false}
                        >
                            <Steps current={this.state.current}>
                                {this.steps.map(item => <Step key={item.title} title={item.title} description={item.description} />)}
                            </Steps>
                        </Card>
                        <br />

                        <Content style={{ background: '#fff' }} >
                        <Row gutter={16}>
                            {/*<Col span={3}>
                               <Card
                                    //title="文档"
                                      //bordered={false}
                                >
                                    <a href="javascript:void(0);"
                                    >委托申请表</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试合同书</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >合同评审表</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试方案书</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试用例表</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试记录表</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试问题清单</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试报告书</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试报告检查表</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >测试工作检查表</a>
                                    <Divider />
                                    <a href="javascript:void(0);"
                                    >满意度调查表</a>
                                </Card>
                            </Col>*/}
                            <Col span={3}>
                                <List
                                    size="small"
                                    header={<div style={letterStyle}>文档</div>}
                                    //footer={<div>Footer</div>}
                                    bordered
                                    //loadMore={loadMore}
                                    dataSource={this.data}
                                    renderItem={item => (<List.Item><a>{item}</a></List.Item>)}
                                    /*renderItem={item => (
                                            <List.Item actions={[<a>edit</a>, <a>more</a>]}>
                                                <List.Item.Meta
                                                    //avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                                    title={<a href="https://ant.design">{item.name.last}</a>}
                                                    description="Ant Design, a design language for background applications, is refined by Ant UED Team"
                                                />
                                                <div>content</div>
                                            </List.Item>
                                        )}*/
                                />
                            </Col>
                            <Col span={21}>
                                <Card title="Card title"
                                      //bordered={false}
                                >Card content</Card>
                            </Col>
                        </Row>
                </Content>
                        <Footer style={{ background: '#fff' }}>
                            <TextArea rows={4} />
                            <Button>
                                取消
                            </Button>
                        </Footer>
                </Layout>

            </div>
        );
    }
}

var letterStyle = {
    /*padding: 10,
    margin: 10,
    backgroundColor: "#fff",
    color: "#333",
    display: "inline-block",
    fontFamily: "monospace",*/
    fontSize: "32",
    textAlign: "center"
};