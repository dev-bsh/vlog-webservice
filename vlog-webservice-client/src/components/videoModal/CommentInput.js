import React from "react";
import InputStyled from "../styled/modalStyled/InputStyled";
import { useSelector } from "react-redux";
const CommentInput = () => {
  const isLoggedIn = useSelector((state) => state.login.isLoggedIn);

  const handleSubmit = () => {
    if (!isLoggedIn) {
      alert("로그인 후 이용가능 합니다.");
      return false;
    }
    /* something do*/
  };
  return (
    <InputStyled placeholder="comment..." onClick={handleSubmit}></InputStyled>
  );
};

export default CommentInput;
