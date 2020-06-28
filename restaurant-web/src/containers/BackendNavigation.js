import React from 'react';
import {useRouteMatch, Link} from "react-router-dom";



function BackendNavigation() {
    let {url} = useRouteMatch();
    let fullPath = window.location.pathname;

    return (
            <ul className="body-menu">
                <li className={`menu-item ${(fullPath.includes(`${url}/cashier`)) ? 'active' : ''}`}>
                    <Link className="item__link" to={`${url}/cashier`}>
                        <div className="item__icon">
                            <i className="fad fa-cash-register"/>
                        </div>
                        <div className="item__hover">
                            <div className="item__hover--header">
                                Thu ngân
                            </div>
                        </div>
                    </Link>
                </li>
                <li className={`menu-item ${(fullPath.includes(`${url}/food`)) ? 'active' : ''}`}>
                    <Link className="item__link" to={`${url}/food`}>
                        <div className="item__icon">
                            <i className="fas fa-drumstick-bite"/>
                        </div>
                        <div className="item__hover">
                            <div className="item__hover--header">
                                Quản lý thực đơn
                            </div>
                        </div>
                    </Link>
                </li>
            </ul>
    );
}

export default BackendNavigation;
