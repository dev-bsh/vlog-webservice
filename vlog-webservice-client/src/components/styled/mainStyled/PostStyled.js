import styled from "styled-components";

const PostStyled = styled.div`
  border-radius: 5px;
  width: 20em;
  height: 19em;
  margin: 1em;
  transition: transform 700ms;
  overflow: hidden;
  &:hover {
    transition: 500ms;
    transform: translateY(-5px);
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2),
      0 6px 20px 12px rgba(0, 0, 0, 0.19);
    opacity: 0.7;
    transition-timing-function: ease-in-out;
  }
`;

export default PostStyled;
