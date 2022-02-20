import { MdFavoriteBorder } from "react-icons/md";
import { MdFavorite } from "react-icons/md";
import react, { useState } from "react";
import LikeStyled from "../styled/modalStyled/LikeStyled";
import { useSelector } from "react-redux";
const Like = () => {
  const [isLiked, setIsLiked] = useState(false);
  const isLoggedIn = useSelector((state) => state.login.isLoggedIn);

  const handleLike = (e) => {
    if (!isLoggedIn) {
      alert("로그인 후 이용가능 합니다.");
      return false;
    }
    if (!isLiked) {
      setIsLiked(true);
    } else {
      setIsLiked(false);
    }
  };
  return isLiked ? (
    <>
      <LikeStyled>
        <MdFavorite onClick={handleLike} />
      </LikeStyled>
    </>
  ) : (
    <>
      <LikeStyled>
        <MdFavoriteBorder onClick={handleLike} />
      </LikeStyled>
    </>
  );
};

export default Like;
