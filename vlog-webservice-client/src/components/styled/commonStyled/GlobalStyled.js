import { createGlobalStyle } from "styled-components";

const GlobalStyled = createGlobalStyle`
a {
  text-decoration: none;
  cursor: pointer;
  color: black;
}
a:visited {
  color: transparent;
}
img {
  cursor: pointer;
}
button {
  color: #718093;
  cursor: pointer;
}

body {
    padding: 0;
    margin: 0;

}

h2 {
  margin-top:0.2em;
  margin-bottom:0.2em;
}
p {
  margin:0.2em;
}
html {
  z-index: 1;
  
}
.Uploader {
  transition: 500ms;
  &:hover {
    color:  #e84118;
    transition: 500ms;
  }
}
input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  overflow: hidden;
  clip:rect(0,0,0,0);
  border: 0;
}

}
`;

export default GlobalStyled;
