/**
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import React from 'react';
import PropTypes from 'prop-types';
import { Scrollbars } from 'react-custom-scrollbars';
import { Button, Form, FormGroup, FormControl, ControlLabel, Col } from 'react-bootstrap';
import Dialog from 'core/view/Dialog';
import FileTree from 'core/view/tree-view/FileTree';
import { exists as checkPathExists } from 'core/workspace/fs-util';

const FILE_TYPE = 'file';

/**
 * File Open Wizard Dialog
 * @extends React.Component
 */
class ImportSwaggerDialog extends React.Component {

    /**
     * @inheritdoc
     */
    constructor(props) {
        super(props);
        this.state = {
            error: '',
            selectedNode: undefined,
            filePath: '',
            showDialog: true,
        };
        this.onFileOpen = this.onFileOpen.bind(this);
        this.onDialogHide = this.onDialogHide.bind(this);
    }

    /**
     * Called when user clicks open
     */
    onFileOpen() {
        const { filePath } = this.state;
        checkPathExists(filePath)
            .then((response) => {
                if (response.exists) {
                    if (response.type === FILE_TYPE) {
                        this.props.importSwaggerPlugin.openSwaggerDefinition(filePath)
                            .then(() => {
                                this.setState({
                                    error: '',
                                    showDialog: false,
                                });
                            })
                            .catch((error) => {
                                this.setState({
                                    error,
                                });
                            });
                    } else {
                        this.setState({
                            error: `${filePath} is not a file`,
                        });
                    }
                } else {
                    this.setState({
                        error: `File ${filePath} does not exist.`,
                    });
                }
            });
    }

    /**
     * Called when user hides the dialog
     */
    onDialogHide() {
        this.setState({
            error: '',
            showDialog: false,
        });
    }

    /**
     * @inheritdoc
     */
    render() {
        return (
            <Dialog
                show={this.state.showDialog}
                title="Open File"
                actions={
                    <Button
                        bsStyle="primary"
                        onClick={this.onFileOpen}
                        disabled={this.state.filePath === ''}
                    >
                        Open
                    </Button>
                }
                closeAction
                onHide={this.onDialogHide}
                error={this.state.error}
            >
                <Form
                    horizontal
                    onSubmit={(e) => {
                        e.preventDefault();
                    }}
                >
                    <FormGroup controlId="filePath">
                        <Col componentClass={ControlLabel} sm={2}>
                            File Path
                        </Col>
                        <Col sm={10}>
                            <FormControl
                                value={this.state.filePath}
                                onKeyDown={(e) => {
                                    if (e.key === 'Enter') {
                                        this.onFileOpen();
                                    } else if (e.key === 'Escape') {
                                        this.onDialogHide();
                                    }
                                }}
                                onChange={(evt) => {
                                    this.setState({
                                        error: '',
                                        filePath: evt.target.value,
                                        selectedNode: undefined,
                                    });
                                }}
                                type="text"
                                placeholder="eg: /home/user/ballerina-services/swagger.json"
                            />
                        </Col>
                    </FormGroup>
                </Form>
                <Scrollbars
                    style={{
                        width: 608,
                        height: 500,
                    }}
                    autoHide
                >
                    <FileTree
                        onSelect={
                            (node) => {
                                this.setState({
                                    error: '',
                                    selectedNode: node,
                                    filePath: node.id,
                                });
                            }
                        }
                        onOpen={
                            (node) => {
                                this.setState({
                                    error: '',
                                    selectedNode: node,
                                    filePath: node.id,
                                });
                                this.onFileOpen();
                            }
                        }
                        extensions={this.props.extensions}
                    />
                </Scrollbars>
            </Dialog>
        );
    }
}

ImportSwaggerDialog.propTypes = {
    importSwaggerPlugin: PropTypes.objectOf(Object).isRequired,
    extensions: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default ImportSwaggerDialog;
