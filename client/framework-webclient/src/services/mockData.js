import {STATE} from "./common";

export const customerData = {
    clientDigest: "dfcbbdfef1f8561cc04cf219736922c07af9be789ca53aa24af6594e89e81ccb",
    functions: [{
      object: "Consign",
      function: "creator",
    },{
      object: "Contract",
      function: "confirmer",
    },{
      object: "Report",
      function: "confirmer",
    }
    ],
};

export const marketingData = {
    clientDigest: "dfcbbdfef1f8561cc04cf219736922c07af9be789ca53aa24af6594e89e81ccb",
    functions: [{
        object: "Consign",
        function: "confirmer",
    },{
        object: "Contract",
        function: "creator",
    },{
        object: "Report",
        function: "creator",
    }
    ],
};

export const mockProjectData = [{
    id : "110",
    name : "快乐星球小杨杰",
    customerId : "151220140",
    status: STATE.CANCELED,
    state : {
        consign : STATE.FINISHED,
        contract : STATE.TO_CHECK,
        testPlan : STATE.TO_SUBMIT,
        testReport : STATE.TO_SUBMIT,
        endProject : STATE.TO_SUBMIT
    }
},{
    id : "120",
    name : "不快乐星球小杨杰",
    customerId : "151220140",
    status: STATE.CANCELED,
    state : {
        consign : STATE.FINISHED,
        contract : STATE.FINISHED,
        testPlan : STATE.TO_SUBMIT,
        testReport : STATE.TO_SUBMIT,
        endProject : STATE.TO_SUBMIT
    }
},{
    id : "119",
    name : "不快乐星球老杨杰",
    customerId : "151220140",
    status: STATE.CANCELED,
    state : {
        consign : STATE.FINISHED,
        contract : STATE.FINISHED,
        testPlan : STATE.FINISHED,
        testReport : STATE.TO_SUBMIT,
        endProject : STATE.TO_SUBMIT
    }
}];