import styled from "styled-components";

const UploaderStyled = styled.div`
  display: flex;
  border: 2px dashed #bdc3c7;
  border-radius: 8px;
  width: 18em;
  height: 28em;
  align-items: center;
  align-content: center;
  justify-content: center;
  transition: 500ms;
  &:hover {
    border: 2px dashed #e84118;
    transition: 500ms;
  }
`;

export default UploaderStyled;
