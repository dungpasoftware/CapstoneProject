import React from 'react';
import {useRouteMatch, Link} from "react-router-dom";



function BackendNavigation() {
    let {url} = useRouteMatch();
    let fullPath = window.location.pathname;

    return (
            <ul className="body-menu">
                <li className={`menu-item ${(fullPath.includes(`${url}/food`)) ? 'active' : ''}`}>
                    <Link className="item__link" to={`${url}/food`}>
                        <div className="item__icon">
                            <i className="fas fa-drumstick-bite"></i>
                        </div>
                        <div className="item__hover">
                            <div className="item__hover--header">
                                Thực đơn
                            </div>
                        </div>
                    </Link>
                </li>
            </ul>
    );
}

export default BackendNavigation;
