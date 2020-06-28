import React, {Component} from 'react';


class BackendCashier extends Component {
    constructor(props) {
        super(props);
        this.state = {
            buttonActive: true
        }
    }

    render() {
        return (
            <div className="be-cashier animate__animated animate__fadeIn animate__faster">
                <div className="cashier-body">
                    <div className="list-table">
                        <div className="table-left">
                            <ul className="list-phong">
                                <li>
                                    <button onClick={ () => {
                                        this.setState({buttonActive: !this.state.buttonActive})
                                        console.log(this.state.buttonActive)
                                    } }
                                            className={`phong__button ${this.state.buttonActive ? 'active' : ''}`}>
                                        Tất cả
                                    </button>
                                </li>
                                <li>
                                    <button className={`phong__button`}>
                                        Tầng 1
                                    </button>
                                </li>
                                <li>
                                    <button className={`phong__button`}>
                                        Tầng 2
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <div className="table-right">
                            <div className="right-top">
                                <div className="right_title">
                                    Yêu cầu thanh toán
                                </div>
                                <div className="right_body">
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                </div>
                            </div>
                            <div className="right-bottom">
                                <div className="right_title">
                                    Chưa yêu cầu
                                </div>
                                <div className="right_body">
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item inuse">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                    <button className="ban-item">
                                        Bàn 1-1
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={'cashier-detail'}>
                    <div className="detail-top">
                        <div className="top-list">
                            <div className="list-item list-header">
                                <div className="item-name">
                                    Món
                                </div>
                                <div className="item-count">
                                    Số lượng
                                </div>
                                <div className="item-cash">
                                    Tiền
                                </div>
                            </div>
                            <div className="list-item">
                                <div className="item-name">
                                    7 Up
                                </div>
                                <div className="item-count">
                                    1
                                </div>
                                <div className="item-cash">
                                    20.000đ
                                </div>
                            </div>
                            <div className="list-item">
                                <div className="item-name">
                                    7 Up
                                </div>
                                <div className="item-count">
                                    1
                                </div>
                                <div className="item-cash">
                                    20.000đ
                                </div>
                            </div>
                            <div className="list-item">
                                <div className="item-name">
                                    7 Up
                                </div>
                                <div className="item-count">
                                    1
                                </div>
                                <div className="item-cash">
                                    20.000đ
                                </div>
                            </div>
                            <div className="list-item">
                                <div className="item-name">
                                    7 Up
                                </div>
                                <div className="item-count">
                                    1
                                </div>
                                <div className="item-cash">
                                    20.000đ
                                </div>
                            </div>
                        </div>
                        <div className="top-note">
                            <div className="note-body">
                                <i className="fad fa-comment-alt-lines"/>
                                <div className="note-content">
                                    Lười hiện tại có họ hàng với loài lười mặt đất là loài lười cổ đã bị tuyệt chủng. Chúng không có răng nanh, sống trên mặt đất và có quan hệ họ hàng với loài lười ngày nay. Lười mặt đất có bộ móng sắc nhọn có thể đứng thẳng bằng hai chân sau. Chúng dài gần 6m và có trọng
                                </div>
                            </div>
                        </div>
                        <div className="top-detail">
                            <div className="detail-item">
                                <div className="item-left">
                                    Tổng tiền:
                                </div>
                                <div className="item-right">
                                    129.999đ
                                </div>
                            </div>
                            <div className="detail-item">
                                <div className="item-left">
                                    Số lượng:
                                </div>
                                <div className="item-right">
                                    5
                                </div>
                            </div>
                            <div className="detail-item">
                                <div className="item-left">
                                    Khách đưa:
                                </div>
                                <div className="item-right">
                                    <input className={'item-input'} type="number"/>
                                    đ
                                </div>
                            </div>
                            <div className="detail-item">
                                <div className="item-left">
                                    Trả lại:
                                </div>
                                <div className="item-right">
                                    129.999đ
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="detail-option">
                        <div className="option-item">
                            <button className="item-btn">
                                <i className="fal fa-retweet"/><br/>
                                Làm mới
                            </button>
                        </div>
                        <div className="option-item">
                            <button className="item-btn">
                                <i className="fal fa-cash-register"/><br/>
                                Thanh toán
                            </button>
                        </div>
                        <div className="option-item dropdown">
                            <button type="button" className="item-btn" data-toggle="dropdown">
                                <i className="far fa-ellipsis-h"/><br/>
                                Menu
                            </button>
                            <div className="dropdown-menu dropdown-menu-right">
                                <button className="dropdown-item">
                                    Cập nhật
                                </button>
                                <button className="dropdown-item">
                                    Tạo mới
                                </button>
                                <button className="dropdown-item">
                                    Hủy hóa đơn
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default BackendCashier;
