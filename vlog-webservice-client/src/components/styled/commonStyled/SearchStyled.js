import styled from "styled-components";

const SearchStyled = styled.input`
  border-radius: 5px;
  border: none;
  height: 2em;
  width: 16vw;
  display: flex;
  justify-content: center;
  align-items: center;
  outline: none;
  font-size: 1.1em;
`;

export default SearchStyled;

// useEffect 써서 리렌더링 안되게 해야함!
