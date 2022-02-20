// actiontype
const TOGGLE_MODAL = "TOGGLE_MODAL";

//action

export const toggledModal = () => (dispatch) => {
  dispatch({ type: TOGGLE_MODAL });
};

// initialState

const initialState = {
  isModalOpened: false,
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case TOGGLE_MODAL:
      return {
        ...state,
        isModalOpened: !state.isModalOpened,
      };
    default:
      return state;
  }
}
