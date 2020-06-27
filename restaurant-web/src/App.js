import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";

import './App.css';
import './asset/css/main.css';
import Navbar from './components/Navbar';
import BackendBody from './components/BackendBody';


function App() {
    return (
        <BrowserRouter>
            <Navbar/>
            <div className="body">
                <Switch>
                    <Route path="/backend">
                        <BackendBody />
                    </Route>
                </Switch>
            </div>
        </BrowserRouter>
    );
}

export default App;
