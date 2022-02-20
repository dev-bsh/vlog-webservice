import styled from "styled-components";

const ModalStyled = styled.div`
  display: flex;
  flex-direction: row;
  position: fixed;
  top: 7.5%;
  left: 50%;
  transform: translate(-50%);
  width: 85%;
  height: 45vw;
  z-index: 4;
  background: white;
  padding: 1em;
  border-radius: 5px;
`;

export default ModalStyled;
