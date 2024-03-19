import Image from "next/image";

import trueAnswer from "/public/Images/trueAnswer.png";

interface rightBoxProps {
  answer: string;
}

export default function RightBox({ answer }: rightBoxProps) {
  return (
    <div className="relative flex justify-center w-48 p-5 text-center align-bottom drop-shadow-md h-28 rounded-3xl bg-f5blue-100">
      <Image src={trueAnswer} alt="trueAnswer" />
      <div className="text-lg font-semibold text-center ">{answer}</div>
    </div>
  );
}
