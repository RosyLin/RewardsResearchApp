import React, {Component} from 'react';
import styled from 'styled-components';
import Table from 'react-bootstrap/Table';
import AuthResponse from './AuthResponse';

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

class AuthRequest extends Component
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

        for (var i =0; i < this.state.AuthRequest.length; i++)
        { 
            rows[i] =
            (
            <TR>
                <TD>{this.state.AuthRequest[i].AUTH_TOKEN_ID}</TD>
                <TD>{this.state.AuthRequest[i].AUTH_RQST_TS}</TD>
                <TD>{this.state.AuthRequest[i].ORD_ID}</TD>
                <TD>{this.state.AuthRequest[i].LL_STORE_ID}</TD>
                <TD>{this.state.AuthRequest[i].RGST_ID}</TD>
                <TD>{this.state.AuthRequest[i].AUTH_FUNC_CD}</TD>
                <TD>{this.state.AuthRequest[i].AUTH_AMT}</TD>
                <TD>{this.state.AuthRequest[i].TRN_TOT_AMT}</TD>
                <TD>{this.state.AuthRequest[i].AUTH_SYS_IND}</TD>
            </TR>
            );
        } 

        var createClickHandler = function(row){
            return function() {
                var cell = row.getElementsByTagName("TD")[0];
                var id = cell.innerHTML;
                alert("id:" + id);

                <AuthResponse></AuthResponse>
              }
            }
            rows[i].onclick = createClickHandler(rows[i]);
        

        return rows;
    }

    render = () => {
        return (
            <TableS>
                <TR>
                    <THHeader >Auth Token ID</THHeader>
                    <THHeader >Auth RQST TS</THHeader>
                    <THHeader >Order ID</THHeader>
                    <THHeader >Store ID</THHeader>
                    <THHeader >RGST ID</THHeader>
                    <THHeader >Auth FUNC CD</THHeader>
                    <THHeader >Auth AMT</THHeader>
                    <THHeader >TRN TOT AMT</THHeader>
                    <THHeader >Auth SYS IND</THHeader>
                </TR>

                <tbody>
                  {this.renderRows()}
                </tbody>
            </TableS>
        )
    }
}

export default AuthRequest;

