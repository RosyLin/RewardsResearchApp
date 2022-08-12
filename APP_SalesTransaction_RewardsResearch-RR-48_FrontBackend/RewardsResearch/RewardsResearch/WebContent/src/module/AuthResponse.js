import React, {Component} from 'react';
import styled from 'styled-components';
import Table from 'react-bootstrap/Table';
import DisplayPurchase from '../api/DisplayPurchase';

const TR = styled.tr`
    cursor: pointer;
    :hover {
        background-color: #67c59b;
    }
`; 

const TableS = styled(Table) `
    margin: 20px;
    border: #0a5740;
    background-color: #dee3e8;
`;

const THHeader = styled.th`
    padding: .6rem;
    color: #0C5641;
    border: solid #0C5641 1.5px;
    font-size: 14px;
    background-color: #dee3e8;
`;

const TD = styled.td`
	word-break: break-word;
	font-size: 14px;
    text-align: center;
    color: #0C5641;
    background-color: #def2e3;
    border: solid #0C5641 1.5px
    padding: .5rem;   
    :hover {
        text-decoration: underline;
    }
`;

class AuthResponse extends Component
{

    constructor(props){
        super(props);

        this.state = {
            ...props.authDetails,
        };

    }
    // rewardsData = this.state;
    
    renderRows = () => { 
        var rows = [];
        console.log(this.state)

        for (var i =0; i < this.state.AuthResponse.length; i++)
        { 
        rows[i] =
        (
        <TR>
            <TD>{this.state.AuthResponse[i].AUTH_TOKEN_ID}</TD>
            <TD>{this.state.AuthResponse[i].PMD_AUTH_RSP_TS}</TD>
            <TD>{this.state.AuthResponse[i].PMD_AUTH_AMT}</TD>
            <TD>{this.state.AuthResponse[i].PMD_AUTH_REAS_CD}</TD>
            <TD>{this.state.AuthResponse[i].AUTH_RSP_TYP_CD}</TD>
            <TD>{this.state.AuthResponse[i].ISD_RSP_CD}</TD>
            <TD>{this.state.AuthResponse[i].REMAIN_BAL_AMT}</TD>
            <TD>{this.state.AuthResponse[i].PMT_MTHD_ID}</TD>
            <TD>{this.state.AuthResponse[i].RSP_TXT_DSPL_DESC}</TD>
            <TD>{this.state.AuthResponse[i].RSP_DESC}</TD>
        </TR>
        );

        var createClickHandler = function(row){
            return function() {
                var cell = row.getElementsByTagName("TD")[0];
                var id = cell.innerHTML;
                alert("id:" + id);

                <DisplayPurchase id></DisplayPurchase>
              }
            }
            rows[i].onclick = createClickHandler(rows[i]);

        } 
        return rows;
    }

    render = () => {
        return (
            <TableS>
                <TR>
                    <THHeader >AUTH Token ID</THHeader>
                    <THHeader >PMD AUTH RSP TS</THHeader>
                    <THHeader >PMD AUTH REAS CD</THHeader>
                    <THHeader >AUTH RSP TYP CD</THHeader>
                    <THHeader >Response Code</THHeader>
                    <THHeader >Remain Balance</THHeader>
                    <THHeader >PMT MTHD ID</THHeader>
                    <THHeader >RSP TXT DSPL DESC</THHeader>
                    <THHeader >RSP DESC</THHeader>
                </TR>

                <tbody>
                  {this.renderRows()}
                </tbody>
            </TableS>
        )
    }
}

export default AuthResponse;

