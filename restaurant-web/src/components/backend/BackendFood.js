import React from 'react';
import {Link, useRouteMatch} from "react-router-dom";

function BackendFood() {
    let {url} = useRouteMatch();

    return (
        <div className="be-food animate__animated animate__fadeIn animate__faster">
            <h2 className="be-title">
                Thực đơn
            </h2>
            <div className="be-select">
                <div className="be-select--left">
                    <input type="text" className="select__name" placeholder={`Tên món ăn`}/>
                    <select defaultValue="0" name="" id="" className="select__type">
                        <option value="0" disabled>Vui lòng chọn</option>
                        <option value="1">Đồ uống có ga</option>
                        <option value="2">Café</option>
                        <option value="3">Abcd</option>
                        <option value="4">Món chính</option>
                        <option value="5">Món tráng miệng</option>
                    </select>
                    <button className="select__search btn-default-green">
                        Tìm kiếm
                    </button>
                </div>
                <div className="be-select--right">
                    <button className="select__add-new btn-default-green">
                        Thêm món mới
                    </button>
                </div>
            </div>
            <div className="food-list">
                <div className="list__title">
                    <i className="fad fa-pizza"/>
                    Danh sách món
                </div>
                <div className="list-body">
                    <div className="list__option">
                        <button className="btn-default-green btn-red">
                            Xoá danh sách đã chọn
                        </button>
                    </div>
                    <table className="list__table">
                        <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" name="" id="" />
                                </th>
                                <td>
                                    Mã sản phẩm
                                </td>
                                <th>
                                    Hình đại diện
                                </th>
                                <th>
                                    Tên món
                                </th>
                                <th>
                                    Nhóm thực đơn
                                </th>
                                <th>
                                    Giá nguyên/ vật liệu
                                </th>
                                <th>
                                    Giá bán (*)
                                </th>
                                <th>
                                    STT
                                </th>
                                <th>
                                    Lựa chọn
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <input type="checkbox" name="" id="" />
                                </td>
                                <td>
                                    7up
                                </td>
                                <td>

                                </td>
                                <td>
                                    7 Up
                                </td>
                                <td>
                                    Đố uống có ga
                                </td>
                                <td>
                                    8,000
                                </td>
                                <td>
                                    20,000
                                </td>
                                <td>
                                    <input className="table__stt" type="number" name="" id="" />
                                </td>
                                <td>
                                    <div className="table__option">
                                        <Link className="btn-default-green btn-xs btn-yellow table__option--link" to={`${url}/edit?id=000`}>
                                            Chỉnh sửa
                                        </Link>
                                        <button className="btn-default-green btn-xs btn-red table__option--delete">Xoá</button>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default BackendFood;