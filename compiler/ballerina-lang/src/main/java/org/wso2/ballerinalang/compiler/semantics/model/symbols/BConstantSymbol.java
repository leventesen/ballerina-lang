/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.ballerinalang.compiler.semantics.model.symbols;

import org.ballerinalang.model.elements.PackageID;
import org.ballerinalang.model.symbols.ConstantSymbol;
import org.ballerinalang.model.symbols.SymbolKind;
import org.wso2.ballerinalang.compiler.semantics.model.types.BType;
import org.wso2.ballerinalang.compiler.util.Name;

import static org.wso2.ballerinalang.compiler.semantics.model.symbols.SymTag.CONSTANT;

/**
 * @since 0.985.0
 */
public class BConstantSymbol extends BSymbol implements ConstantSymbol {

    public Object literalValue;
    public int literalValueTypeTag;
    public BType literalValueType;

    public BConstantSymbol(int flags, Name name, PackageID pkgID, BType finiteType, BType valueType, BSymbol owner) {
        super(CONSTANT, flags, name, pkgID, finiteType, owner);
        this.literalValueType = valueType;
    }

    @Override
    public SymbolKind getKind() {
        return SymbolKind.CONSTANT;
    }
}
