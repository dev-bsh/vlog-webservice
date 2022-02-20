import React from "react";
import PostFormStyled from "../styled/uploadStyled/PostFormStyled";
import InputWrapper from "../styled/uploadStyled/InputWrapper";
import DescriptionInput from "../styled/uploadStyled/DescriptionInput";
import CaptionInput from "../styled/uploadStyled/CaptionInput";
import Uploader from "./Uploader";

const PostForm = () => {
  return (
    <>
      <PostFormStyled>
        <h3>Upload Your Day</h3>
        <Uploader />
      </PostFormStyled>
      <InputWrapper>
        <p>Caption</p>
        <CaptionInput />
        <p>Description</p>
        <DescriptionInput />
      </InputWrapper>
    </>
  );
};

export default PostForm;
