import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";

import './App.css';
import './asset/css/main.css';
import Navbar from './components/Navbar';
import BackendBody from './components/backend/BackendBody';


class App extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        return (
            <BrowserRouter>
                <Navbar/>
                <div className="body">
                    <Switch>
                        <Route path="/backend">
                            <BackendBody/>
                        </Route>

                    </Switch>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;
