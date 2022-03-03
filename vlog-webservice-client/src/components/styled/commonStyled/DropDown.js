import styled from "styled-components";

const DropDown = styled.div`
  display: flex;
  visibility: ${(props) => (props.visible ? "visible" : "hidden")};
  flex-direction: column;
  position: absolute;
  justify-content: space-around;
  z-index: 5;

  background-color: white;
  border-radius: 4px;
  margin-top: 4px;
  padding: 8px;
  height: 6em;
  width: 6em;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
`;

export default DropDown;
