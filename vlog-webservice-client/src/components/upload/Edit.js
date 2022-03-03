import React, { useState } from "react";
import ButtonWrapper from "../styled/uploadStyled/ButtonWrapper";
import UploadButtonStyled from "../styled/uploadStyled/UploadButtonStyled";
import UploadWrapper from "../styled/uploadStyled/UploadWrapper";
import InputWrapper from "../styled/uploadStyled/InputWrapper";
import DescriptionInput from "../styled/uploadStyled/DescriptionInput";
import CaptionInput from "../styled/uploadStyled/CaptionInput";
import PostForm from "../upload/PostForm";
import UploaderStyled from "../styled/uploadStyled/UploaderStyled";
import PostFormStyled from "../styled/uploadStyled/PostFormStyled";
import FormStyled from "../styled/uploadStyled/FormStyled";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { MdPresentToAll } from "react-icons/md";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import Cookies from "universal-cookie";
import { useSelector } from "react-redux";
import expireToken from "../../utils/expireToken";
toast.configure();

const UploadTest = () => {
  const cookies = new Cookies();
  /* tag 분리 하는 로직 */
  const [tagList, setTagList] = useState([]);
  const [isValid, setIsVaild] = useState("black");
  const [tag, setTag] = useState("");
  const navigate = useNavigate();
  const userData = cookies.get("user");
  const editPost = useSelector((state) => state.post.postDetail);
  const handleChange = (e) => {
    if (e.keyCode == 32) {
      setTag("");
    }
    setTag(e.target.value);
  };

  const handleKeyDown = (e) => {
    if (e.keyCode == 32) {
      setTagList([...tagList, tag]);
    }
  };

  const [inputs, setInputs] = useState({
    userId: ``,
    tags: `${editPost.tags}`,
    description: `${editPost.description}`,
    video: "",
  });

  const onChange = (e) => {
    setInputs({
      ...inputs,
      [e.target.name]: e.target.value,
    });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file !== null) {
      setIsVaild("#4cd137");
    }
    const video = file;

    setInputs({
      ...inputs,
      [e.target.name]: video,
    });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("userId", userData.userId);
    formData.append("tags", inputs.tags);
    formData.append("description", inputs.description);
    expireToken();
    await axios
      .put(`/api/v2/posts/${editPost.postsId}`, formData, {
        headers: {
          encType: "multipart/form-data",
          ACCESS_TOKEN: cookies.get("user").accessToken,
        },
      })
      .then((res) => {
        //handle success
        navigate("/");
        toast.success("수정 되었습니다. :)", {
          position: toast.POSITION.TOP_CENTER,
        });
      })
      .catch((err) => {
        //handle error
        toast.error("수정에 실패하였습니다. :(", {
          position: toast.POSITION.TOP_CENTER,
        });
        navigate("/");
      });
  };

  return (
    <>
      <UploadWrapper>
        <form onSubmit={handleSubmit}>
          <FormStyled>
            <InputWrapper>
              <CaptionInput
                type="text"
                name="tags"
                placeholder="Input Tags"
                value={inputs.tags}
                onChange={onChange}
                onKeyDown={handleKeyDown}
              />
              <DescriptionInput
                type="text"
                name="description"
                placeholder="Description"
                value={inputs.description}
                onChange={onChange}
              />

              <ButtonWrapper>
                <UploadButtonStyled>Edit</UploadButtonStyled>
              </ButtonWrapper>
            </InputWrapper>
          </FormStyled>
        </form>
      </UploadWrapper>
    </>
  );
};

export default UploadTest;
