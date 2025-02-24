package com.ismerioeyu.blowyexter.modules;

import com.ismerioeyu.blowyexter.BlowyExter;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.text.Text;

public class ModuleTest extends Module {
    // Các biến để theo dõi tiến trình thực hiện
    private int phase = 0;      // 1: nhảy, 2: ngồi, 3: dùng
    private int tickCounter = 0;
    private int jumpCount = 0;
    private int sitCount = 0;

    public ModuleTest() {
        // Sử dụng CATEGORY đã được khai báo trong BlowyExter
        super(BlowyExter.CATEGORY, "test-module", "Module thử nghiệm: gửi chat, nhảy, ngồi và dùng.");
    }

    @Override
    public void onActivate() {
        tickCounter = 0;
        jumpCount = 0;
        sitCount = 0;
        phase = 1; // Bắt đầu với pha nhảy

        // Gửi tin nhắn "Đây là thử nghiệm" ra chat khi bật module
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("Đây là thử nghiệm"));
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        tickCounter++;

        switch (phase) {
            case 1 -> { // Pha nhảy: thực hiện 2 lần nhảy
                // Mỗi 20 tick (khoảng 1 giây nếu server 20 tps) thực hiện 1 lần nhảy
                if (tickCounter >= 20) {
                    if (mc.player != null) {
                        mc.player.jump();
                    }
                    jumpCount++;
                    tickCounter = 0;
                    if (jumpCount >= 2) {
                        phase = 2; // Chuyển sang pha ngồi
                    }
                }
            }
            case 2 -> { // Pha ngồi: thực hiện 5 lần "ngồi" (sneak)
                if (tickCounter >= 20) {
                    // Giả lập hành động nhấn phím ngồi (sneak)
                    mc.options.keySneak.setPressed(true);
                    // Giải phóng ngay sau đó để module không bị kẹt ở trạng thái ngồi
                    mc.options.keySneak.setPressed(false);
                    sitCount++;
                    tickCounter = 0;
                    if (sitCount >= 5) {
                        phase = 3; // Chuyển sang pha "nhả và dùng"
                    }
                }
            }
            case 3 -> { // Pha "nhả và dùng": mô phỏng thao tác dùng vật phẩm
                mc.options.keyUse.setPressed(true);
                mc.options.keyUse.setPressed(false);

                // Gửi tin nhắn hoàn thành ra chat
                if (mc.player != null) {
                    mc.player.sendMessage(Text.literal("[BlowyExter] đã hoàn thành!"));
                }
                phase = 4; // Kết thúc chuỗi hành động
                // Tự tắt module sau khi hoàn thành
                this.toggle();
            }
            default -> {
                // Không làm gì ở các pha khác
            }
        }
    }

    @Override
    public void onDeactivate() {
        // Đảm bảo rằng không có phím nào bị kẹt khi module tắt
        mc.options.keySneak.setPressed(false);
        mc.options.keyUse.setPressed(false);
        mc.options.keyJump.setPressed(false);
    }
}
