import styled from "styled-components";

const CaptuonInput = styled.input`
  width: 35em;
  height: 2.5em;
  border-radius: 4px;
  border: 1px solid #bdc3c7;
  transition: 500ms;
  margin-bottom: 2em;
  &:focus {
    outline: none;
    margin: 0;
    font-size: 1em;
    transition: 500ms;
  }
`;

export default CaptuonInput;
