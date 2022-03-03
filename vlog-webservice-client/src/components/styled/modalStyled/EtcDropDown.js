import styled from "styled-components";

const EtcDropDown = styled.div`
  display: ${(props) => (props.visible ? "flex" : "none")};

  justify-content: space-around;
  z-index: 5;

  background-color: white;
  border-radius: 4px;
`;

export default EtcDropDown;
