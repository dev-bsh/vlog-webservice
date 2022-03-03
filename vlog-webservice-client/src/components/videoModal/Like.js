import { MdFavoriteBorder } from "react-icons/md";
import { MdFavorite } from "react-icons/md";
import react, { useState, useEffect } from "react";
import LikeStyled from "../styled/modalStyled/LikeStyled";
import { useSelector, useDispatch } from "react-redux";
import Cookies from "universal-cookie";
import axios from "axios";
import {
  requestLike,
  requestLikeCancel,
  fetchLike,
} from "../../redux/module/like";

const Like = () => {
  const dispatch = useDispatch();
  const { postsId } = useSelector((state) => state.post.postDetail);
  const likeState = useSelector((state) => state.like.isLiked);
  const [isLiked, setIsLiked] = useState(likeState);
  const cookies = new Cookies();
  const isLoggedIn = cookies.get("isLoggedIn");
  const user = cookies.get("user");

  const handleLike = async (e) => {
    if (!isLoggedIn) {
      alert("로그인 후 이용가능 합니다.");
      return false;
    }
    if (!isLiked) {
      dispatch(requestLike(postsId, user.userId));
      setIsLiked(true);
    } else {
      dispatch(requestLikeCancel(postsId, user.userId, user.accessToken));
      setIsLiked(false);
    }
  };

  const compareLike = async () => {
    if (isLoggedIn) {
      const response = await axios.get(`/api/v1/user/${user.userId}/like`);
      if (response.data.userLikePostIds.includes(postsId)) {
        setIsLiked(true);
      } else {
        setIsLiked(false);
      }
    }
  };

  useEffect(() => {
    compareLike();
  }, [postsId]);

  return (
    <>
      {isLiked ? (
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
      )}
    </>
  );
};

export default Like;
