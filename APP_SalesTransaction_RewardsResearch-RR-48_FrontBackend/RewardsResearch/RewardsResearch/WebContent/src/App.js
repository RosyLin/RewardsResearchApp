import './App.css';
import Button from './module/Button';
import React, {useState} from "react";
import InputForm from './module/InputForm';
import DisplayPurchase from './api/DisplayPurchase';
import styled from 'styled-components';

const reloadPage = () => {
  window.location.reload()
}

const Row = styled.tr`
    border-radius: 6px;
    padding: 10px;
    min-width: 2000px;
    margin-top: 20px;
    margin-bottom: 50px;
    text-align: center;
    background-color: rgba(222,227,232,0.6);
`; 

const H1 = styled.h1`
    font-family: Georgia;
    font-size: 22px;
    font-weight: bold;
    color: #86d5c1;
    margin: 0px;
`; 

const H2 = styled.h1`
    font-family: Georgia;
    font-size: 22px;
    font-weight: bold;
    color: #179b76;
    margin: 0px;
`; 

const H3 = styled.h1`
    font-family: Georgia;
    font-size: 22px;
    font-weight: bold;
    color: #117458;
    margin: 0px;
`; 


const H4 = styled.h1`
    font-family: Georgia;
    font-size: 22px;
    font-weight: bold;
    color: #0F674E;
    margin: 0px;
`; 

function App() {
  const [show, setShow] = useState(true);
  const [hidden, setHidden] =useState(true);

  return (
    <div className="App">
      <body className="App-body">

      <Row>
          <H1> Bean Bucks Rewards Research App </H1>
          <H2> Bean Bucks Rewards Research App </H2>
          <H3> Bean Bucks Rewards Research App </H3>
          <H4> Bean Bucks Rewards Research App </H4>
      </Row>

      
     {/* {hidden ? <InputForm></InputForm> : null}
      <br/> */}

      {/* {hidden ? <Button onClick={async () => {console.log(await api.getByOrderID("010011542174"))}}> */}
      {/* {hidden ? <Button onClick={async () => {console.log(await api.getCustomer("00357935531"))}}>
        SEARCH! */}
      {/* {hidden ? <InputForm onClick={() => setHidden(s => !s)}> SEARCH! </InputForm>: null}; */}

        <InputForm></InputForm>
        
        <br/>
        <DisplayPurchase></DisplayPurchase>

        <br/>
        <Button onClick={reloadPage}>REFRESH</Button>
        <br/>

        <a
          className="App-link"
          href="https://www.llbean.com"
          target="_blank"
          rel="noopener noreferrer"
        >
          <br/>
          <br/>
          Go to llbean.com
          <br/>
          <br/>
        </a>

      </body>
    </div>
    
  );
}

export default App;
