import React from 'react';
import {useRouteMatch, Route} from "react-router-dom";
import BackendNavigation from "../containers/BackendNavigation";
import BackendFood from "./BackendFood";


function BackendBody() {
    let {path} = useRouteMatch();

    return (
        <div className="rs-body">
            <BackendNavigation />
            <Route path={`${path}/food`}>
                <BackendFood />
            </Route>
        </div>
    );
}

export default BackendBody;
