import Alert from 'react-bootstrap/Alert';
import React, { Component } from 'react';
import Button from './Button';
import styled from 'styled-components';


const MyAlert = styled(Alert)`
    max-width: 400px;
    color: #b02f15;
    border: solid;
    border-color: #ffeeba;
    padding: 1rem 2rem;
    margin: 10px;
    position: relative;
    text-align: center;
    border-radius: 7px;
    background-color: #fff3cd;
    font-size: 13px;
`
const CloseButton = styled(Button) `
	color:#b02f15;
    width: 60px;
	height: 30px;
    font-size: 12px;
    font-weight: bold;
`;

const MyHr = styled.hr`
    border: 1.5px solid;
`
class AlertShow extends Component {

    constructor(props){
        super(props);

        this.state = {
            show : true,
            ...props
        };

    }

    setShow = (bool) => {
        this.setState({
            show : bool
        });

    }

    render = () => {

        if(!this.state.show){
            return null;
        }

        return (
                <MyAlert variant={this.state.variant} onClose={() => this.setShow(false)} dismissible>
                <h4>Warning!</h4>
                
                <MyHr />
                
                <p>
                    {this.state.messageBody}
                </p>

                </MyAlert>
            );
    }  
    // return <Button onClick={() => setShow(true)}>ALERT!</Button>
}
export default AlertShow;