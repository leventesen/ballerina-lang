// Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/http;

http:AuthProvider jwtAuthProvider3 = {
    scheme: http:JWT_AUTH,
    config: {
        issuer: "ballerina",
        audience: ["ballerina"],
        certificateAlias: "ballerina",
        trustStore: {
            path: "${ballerina.home}/bre/security/ballerinaTruststore.p12",
            password: "ballerina"
        }
    }
};

listener http:Listener listener09 = new(9100, config = {
    authProviders: [jwtAuthProvider3],
    secureSocket: {
        keyStore: {
            path: "${ballerina.home}/bre/security/ballerinaKeystore.p12",
            password: "ballerina"
        }
    }
});

@http:ServiceConfig {
    authConfig: {
        scopes: ["test-scope"]
    }
}
service echo9 on listener09 {

    resource function test9(http:Caller caller, http:Request req) {
        _ = caller->respond(());
    }
}
