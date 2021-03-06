// Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

# Represents UDP socket client and related remote functions.
#
# + localPort - the local port number to which this socket is bound
# + localAddress - the local IP address string in textual presentation to which the socket is bound
# + id - a unique identifier to identify each client
public type UdpClient client object {

    private Address localAddress;
    public int localPort = 0;
    public string? interface = ();
    public int id = 0;

    # Initialize the UDP client based on provided configuration.
    #
    # + localAddress - Locally binding interface and port
    public function __init(Address? localAddress = ()) {
        var initResult = self.initEndpoint(localAddress);
        if (initResult is error) {
            panic initResult;
        }
        if (localAddress is Address) {
            self.localAddress = localAddress;
        }
    }

    extern function initEndpoint(Address? localAddress) returns error?;

    # Send given data to the specified remote client.
    #
    # + content - the content that wish to send to the client socket
    # + address - the address of the remote client socket
    # + return - number of bytes got written or an error if encounters an error while writing
    public remote extern function sendTo(byte[] content, Address address) returns int|error;

    # Reads data from the remote client. If the data has the specified length, then wait until that number of bytes
    # are received from the client. Else, return the data available in the OS buffer or wait until data receive.
    # If the request length is lesser than the data in the buffer, then the rest will be discarded.
    #
    # + length - Positive integer. Represents the number of bytes which should be read
    # + return - Content as a byte array, the number of bytes read and the address of the sender
    # or an error if encounters an error while reading
    public remote extern function receiveFrom(int length = -100) returns (byte[], int, Address)|error;

    # Closes the client socket connection.
    #
    # + return - - an error if encounters an error while closing the connection or returns nil otherwise
    public remote extern function close() returns error?;
};

# This represent the IP socket address.
#
# + host - The hostname of the Socket Address
# + port - The port number of the Socket Address
public type Address record {|
    string host?;
    int port;
|};
