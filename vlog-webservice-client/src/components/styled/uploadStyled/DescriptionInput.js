import styled from "styled-components";

const DescriptionInput = styled.input`
  width: 35em;
  height: 15em;
  border-radius: 4px;
  border: 1px solid #bdc3c7;
  transition: 500ms;

  &:focus {
    outline: none;
    margin: 0;
    font-size: 1em;
    transition: 500ms;
  }
`;

export default DescriptionInput;
