import styled from "styled-components";

const UploadButtonStyled = styled.button`
  background-color: #ea8685;
  border-radius: 4px;
  border: none;
  font-size: 1.5em;
  color: #ecf0f1;
  padding: 0.5em 1.7em;
  transition: background-color 700ms;
  &:hover {
    background-color: #ff3f34;
    transition: 500ms;
    transition-timing-function: ease-in-out;
  }
`;

export default UploadButtonStyled;
