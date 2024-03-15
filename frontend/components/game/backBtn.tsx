"use client";
import { useRouter } from "next/navigation";
import { IoChevronBackSharp } from "react-icons/io5";

export default function BackBtn() {
  const router = useRouter();

  const goBackHandler = () => {
    router.back();
  };
  return (
    <div onClick={goBackHandler}>
      <button><IoChevronBackSharp size={26}/></button>
    </div>
  );
}
