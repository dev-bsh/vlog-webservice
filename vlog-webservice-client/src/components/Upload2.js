import React from "react";
import UploadWrapper from "../styled/uploadStyled/UploadWrapper";
import ButtonWrapper from "../styled/uploadStyled/ButtonWrapper";
import PostForm from "./PostForm";
import UploadButtonStyled from "../styled/uploadStyled/UploadButtonStyled";

const Upload = () => {
  return (
    <>
      <UploadWrapper>
        <PostForm />
      </UploadWrapper>
      <ButtonWrapper>
        <UploadButtonStyled>upload</UploadButtonStyled>
      </ButtonWrapper>
    </>
  );
};

export default Upload;
