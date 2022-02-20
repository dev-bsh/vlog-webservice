import styled from "styled-components";

const HeaderStyled = styled.header`
  display: flex;
  align-items: center;
  border-bottom: 1px solid #ccc;
  justify-content: space-around;

  position: sticky;
  top: 0;
  background: white;
  height: 4em;
  min-height: 4em;
  z-index: 2;
  padding: 0 3em;
`;

export default HeaderStyled;
