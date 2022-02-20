import styled from "styled-components";

const CloseButton = styled.button`
  position: fixed;
  right: 0;
  background-color: transparent;
  border: none;
  color: white;
  font-size: 2em;
  margin: 0.5em;

  &:hover {
    font-size: 2.09em;
    transition: 500ms;
  }
`;

export default CloseButton;
