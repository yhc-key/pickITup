import Image from "next/image";

import right from "/public/Images/right.png";

interface rightBoxProps {
  answer: string;
}

export default function RightBox({ answer }: rightBoxProps) {
 
  return (
    <div className="relative p-5 text-center align-bottom drop-shadow-md w-52 h-28 rounded-3xl bg-f5blue-100">
    <Image src={right} alt="right" />
    <div className="text-lg font-semibold text-center ">
    {answer}
    </div>
  </div>
  );
}
