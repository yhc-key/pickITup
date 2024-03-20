import { CreateTypes } from "canvas-confetti";
import { useEffect, useRef } from "react";
import ReactCanvasConfetti from "./reactCanvasConfetti";

const Realistic = () => {
  const animationInstance = useRef<CreateTypes | null>(null);

  const makeShot = (particleRatio: number, opts: object) => {
    animationInstance.current &&
      animationInstance.current({
        ...opts,
        origin: { y: 0.8 },
        particleCount: Math.floor(200 * particleRatio),
      });
  };

  const fire = () => {
    makeShot(0.25, {
      spread: 25,
      startVelocity: 55,
    });
    makeShot(0.2, {
      spread: 20,
    });
    makeShot(0.35, {
      spread: 100,
      decay: 0.91,
      scalar: 0.8,
    });
    makeShot(0.1, {
      spread: 120,
      startVelocity: 25,
      decay: 0.92,
      scalar: 1.2,
    });

    makeShot(0.1, {
      spread: 120,
      startVelocity: 45,
    });
  };

  const getInstance = (instance: CreateTypes | null) => {
    animationInstance.current = instance;
  };

  useEffect(() => {
    // 컴포넌트가 마운트되면 폭죽을 표시합니다.
    fire();
  }, []);

  return (
    <div>
      <ReactCanvasConfetti refConfetti={getInstance} className="canvas" />
    </div>
  );
};

export default Realistic;
