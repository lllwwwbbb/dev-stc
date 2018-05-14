import React, {Component, PropTypes} from 'react';
import {Row, Col, Card, Tabs, Select, Button, Layout, Form, Input,Radio,Checkbox,Icon} from 'antd';

const FormItem=Form.Item;
const RadioGroup = Radio.Group;
const CheckboxGroup = Checkbox.Group;
const { TextArea } = Input;

class ConsignContentComponent extends Component {
    constructor(props) {
        super(props);
    }

    static defaultProps = {
        values: {},
        save: console.log,
        disable:false,
    };

    static propTypes = {
        values: PropTypes.object.isRequired,
        save: PropTypes.func.isRequired,
        disable: PropTypes.bool.isRequired,
    };

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.props.save(values);
            }
        });
    };


    render() {
        const { getFieldDecorator } = this.props.form;
        const formItemLayout =  {
            labelCol: { span: 5 },
            wrapperCol: { span: 18 },
        };

         const longFILayout =  {
            labelCol: { span: 12 },
            wrapperCol: { span: 10 },
        };

        const buttonItemLayout =  {
            wrapperCol: { span: 14, offset: 4 },
        } ;


        return(
            <Form onSubmit={this.handleSubmit}>

                <FormItem {...formItemLayout}>
                    <h1>软件项目委托测试申请书</h1>
                </FormItem>


                <FormItem>请用✓选择：○——单选； ◻——多选。</FormItem>


                <FormItem {...formItemLayout} label="测试类型">
                    {getFieldDecorator('test_type', {
                        rules: [{ required: true, message: '请选择至少一项测试类型!'}],
                    })(
                        <Checkbox.Group disabled={this.props.disable}>
                            <Checkbox value={"软件确认测试"}/>软件确认测试
                            <Checkbox value={"成果/技术鉴定测试"}/>成果/技术鉴定测试
                            <Checkbox value={"专项资金验收测试"}/>专项资金验收测试
                            <Checkbox value={"其他"}/>其他：
                        </Checkbox.Group>
                    )}
                </FormItem>


                <FormItem {...formItemLayout} label="请输入软件名称">
                    {getFieldDecorator('software_name', {
                        rules: [{ required: true, message: '请输入软件名称！' }],
                    })(
                        <Input disabled={this.props.disable}/>
                    )}
                    </FormItem>


                <FormItem {...formItemLayout} label={"版本号"}>
                    {getFieldDecorator('version', {
                        rules: [{ required: true, message: '请正确输入版本号！',pattern:"^[a-zA-Z0-9/.]+$"}],
                    })(
                        <Input disabled={this.props.disable}/>
                    )}
                    </FormItem>


        );
    }
}
export default Form.create()(ConsignContentComponent);